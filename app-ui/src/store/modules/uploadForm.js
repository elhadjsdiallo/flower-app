import { sendRequest, getAxiosDefaultParams } from "@/utilis/AxiosUtil";
import { createHelpers } from "vuex-map-fields";

const { getFLOWERField, updateFLOWERField } = createHelpers({
  getterType: "getFLOWERField",
  mutationType: "updateFLOWERField",
});

export default {
  state: {
    userFlower: {
      adresse: null,
      flowerImage: null,
      description: null,
      longitude: null,
      latitude: null,
      
    },
    userListFlowers: [],
    flowerToDisplay:null,
    flowers: [],
    dialog: false,
  },

  getters: {
    getFLOWERField,
  },
  mutations: {
    updateFLOWERField,
    SET_USER_FLOWERS(state, payload) {
      state.userListFlowers = payload;
    },
    SET_FLOWERS(state, payload) {
      state.flowers = payload;
    },
    SET_FLOWER(state, payload) {
      state.flowerToDisplay = payload;
    },
  },
  actions: {
    getUserFlower: async ({ commit }, { userId }) => {
      let response = await sendRequest(
        "get",
        null,
        "/flower/image/user/" + userId
      );
      if (response.status == 200) {
        commit("SET_USER_FLOWERS", response.data);
      }

      return response;
    },
    getAllFlowers: async ({ commit }) => {
      let response = await sendRequest("get", null, "/flowers");
      if (response.status == 200) {
        commit("SET_FLOWERS", response.data);
      }

      return response;
    },
    getFlower: async ({ commit },{flowerId}) => {
      let response = await sendRequest("get", null, "/flower/"+flowerId);
      if (response.status == 200) {
        commit("SET_FLOWER", response.data);
      }

      return response;
    },
    deleteFlower: async ({ commit },{flowerId}) => {
      let response = await sendRequest("delete", null, "/flower/"+flowerId);
      if (response.status == 200) {
        commit("SET_FLOWER", response.data);
      }

      return response;
    },
    uploadFlowerImage: async ({}, { userId, userFlower, position }) => {
      let dataForm = new FormData();
      let response;

      dataForm.append("image", userFlower.flowerImage);
      dataForm.append("description", userFlower.description);
      dataForm.append("adresse", userFlower.adresse);
      dataForm.append("latitude", position.latitude);
      dataForm.append("longitude", position.longitude);

      let axiosParams = getAxiosDefaultParams();

      axiosParams.image = true;

      axiosParams.headers["Content-Type"] =
        "`multipart/form-data;boundary=${data._boundary}`";
      response = await sendRequest(
        "post",
        dataForm,
        "/flower/image/user/" + userId,
        axiosParams
      );

      return response;
    },
  },
};
