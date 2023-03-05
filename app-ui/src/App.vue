<!-- eslint-disable vue/max-attributes-per-line -->
<template>
  <v-app>
    <!-- Entete de l'application -->
    <nav-bar></nav-bar>


    <!-- Fin de l'entete de l'application-->

    <!-- corps de la pagede l'application-->
    <v-main>
      <custom-alert></custom-alert>
      <!--quand tu cliques sur un lien par exemple /home  il te redirige vers le composant home-->
      <router-view />
    </v-main>
    <!-- fin du contenu du corps -->


    <!-- debut pied de page -->
    <mybottom-navigation></mybottom-navigation>
  </v-app>

</template>

<script>
import CustomAlert from './components/errors_utils/CustomAlert.vue';
import MybottomNavigation from './components/MybottomNavigation.vue';
import NavBar from './components/NavBar.vue';
export default {
  components: { MybottomNavigation, NavBar, CustomAlert },


  data: () => ({
    //
  }),
};
</script>
