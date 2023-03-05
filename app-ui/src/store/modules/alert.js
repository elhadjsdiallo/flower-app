export default {
    state: {
      errorMessage: 'Erreur de message par défaut à changer',
      alertType: "error",
      showError: false,
    },
  
    getters: {
      getErrorMessage: (state) => {
        return state.errorMessage;
      },
      getAlertType: (state) => {
        return state.alertType;
      },
      getShowError: (state) => {
        return state.showError;
      },
    },
    mutations: {
      SET_ALERT_INFO(state, payload) {
        switch (payload.type) {
          case "message":
            state.errorMessage=payload.data;
            break;
          case "alertype":
            state.alertType = payload.data;
            break;
          case "display":
            state.showError = payload.data;
            break;
  
          default:
            break;
        }
      },
    },
    actions: {
  
      displayAlert:({commit},{type,data})=>{
  
          commit('SET_ALERT_INFO',{type:type,data:data})
  
      },
      displayBanner:({commit},{message,type,activate})=>{
  
        commit('SET_ALERT_INFO',{type:"message",data:message})
        commit('SET_ALERT_INFO',{type:"alertype",data:type})
        commit('SET_ALERT_INFO',{type:"display",data:activate})
  
  
    },
   
  
    },
  };