<template>
  <div>
    
    <my-maps v-if="user && userAuthenticated"></my-maps>
    <connexion-display v-else></connexion-display>
  </div>
</template>

<script>
import MyMaps from "@/components/MyMaps.vue";
import { GlobalUserComputed } from "@/utilis/GlobalUserComputed";
import ConnexionDisplay from "./ConnexionDisplay.vue";

export default {
  name: "Home",

  components: {
    MyMaps,
    ConnexionDisplay,
  },
  mixins: [GlobalUserComputed],
  data() {
    return {
      isConnected: false,
    };
  },
  mounted() {
    console.log(this.userAuthenticated);
    this.isConnected = this.user ? this.userAuthenticated : false;
  },
};
</script>
