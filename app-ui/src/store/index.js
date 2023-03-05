import Vue from 'vue';
import Vuex from 'vuex';
import user from './modules/user';
import createPersistedState from "vuex-persistedstate";
import alert from './modules/alert';
import uploadForm from './modules/uploadForm';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
  },
  plugins: [createPersistedState({
    paths :['user.user'],

  })],
  modules: {
    user,
    alert,
    uploadForm
  },
});
