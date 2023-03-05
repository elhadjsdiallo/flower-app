import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

Vue.use(Vuetify);
const vuetify = new Vuetify({
  theme: {
    themes: {
      light: {
        primary: '#FFFFFF', //white
        secondary: '#4CAF50', //green

      },
    },
  },
})

export default vuetify
