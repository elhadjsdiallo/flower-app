import { createHelpers } from "vuex-map-fields";
import { sendRequest,getAxiosDefaultParams } from "@/utilis/AxiosUtil";

const { getUSERField, updateUSERField } = createHelpers({
  getterType: "getUSERField",
  mutationType: "updateUSERField",
});

const appToken = localStorage.getItem("appToken");

const userDefault = {
  firstName: "",
  lastName: "",
  email: "",
  password: "",
  passwordConfirmation: "",
};

export default {
  state: {
    user: userDefault,

    appToken: appToken || "",
    userAuthenticated: !!appToken,
  },

  getters: {
    getUSERField,
    isAuthenticated(state) {
      return state.userAuthenticated;
    },

    appToken(state) {
      return state.appToken;
    },
  },
  mutations: {
    updateUSERField,


    SET_USER(state, payload) {
      state.user = {
        ...state.user,
        ...payload,
        ...{
          password: "",
          passwordConfirmation: "",
    
        },
      };
    },

    authenticated(state, value) {
      state.userAuthenticated = value;
    },

    setToken(state, appToken) {
      state.appToken = appToken;
    },
    // resetState (state) {

    //   Object.keys(initial).forEach(key => { state[key] = initial[key] })
    // },

    //TODO voir avec sadou pour le reset.
    resetUser(state) {
      state.user = userDefault;
    },
  },
  actions: {
    /**
     * Ajoute un utilisateur dans la base de donnée.
     */

    async createUser(_, user) {
      localStorage.removeItem("appToken");
      let extraParams = getAxiosDefaultParams();
      extraParams.basePath = "public";
      return  sendRequest("post", user, "/register", extraParams);

    },
     /**
     * Recupère l'utilisateur courant connecté.
     * @returns
     */
     getCurrentUser: async ({ commit }) => {
        let response = await sendRequest("get", null, "/users/current/");
    
  
        commit("SET_USER", response.data);
        return response;
      },

    /**Sauvegarde le token */
    saveToken(_, appToken) {
      if (appToken) {
        localStorage.setItem("appToken", appToken);
      } else {
        localStorage.removeItem("appToken");
      }
    },

    /**
     * log un utilisateur.
     * @param {*} _
     * @param {*} param1
     */
    async login({ commit, dispatch }, data) {
      let extraParams = getAxiosDefaultParams();
      extraParams.basePath = "public";

      let response = await sendRequest("post", data, "/signin", extraParams);
      if (response.status == 200) {
        let tokenWithType = "Bearer "+ response.data.token;

        dispatch("saveToken", tokenWithType);
        commit("setToken", tokenWithType);
        commit("authenticated", true);
      }
      return response;
    },

    saveOauthToken({ commit, dispatch }, { tokenWithType }) {
      dispatch("saveToken", tokenWithType);
      commit("setToken", tokenWithType);
      commit("authenticated", true);
      commit("resetUser");
    },
     
    clearUserToken({commit})
    {
      commit("resetUser");
      localStorage.removeItem("appToken");
      commit("authenticated", false);
    },

    /**update user image profile */



  },
};
