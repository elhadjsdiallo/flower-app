<template>
  <div>
    <!--eslint-disable-next-line-->
    <!-- <v-navigation-drawer app width="100%">
      <v-row class="fill-height" no-gutters>


      </v-row>
    </v-navigation-drawer>  -->
    <v-navigation-drawer v-model="drawer" absolute temporary>
      <v-list-item class="px-2">
        <v-list-item-avatar>
          <v-icon @click="drawer = !drawer">mdi-menu</v-icon>
        </v-list-item-avatar>
      </v-list-item>

      <v-divider></v-divider>

      <v-list dense nav>
        <v-list-item :to="item.path" v-for="item in items" :key="item.title">
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
      <template v-slot:append>
        <v-list dense nav>
          <v-list-item v-for="item in bottom_items" :key="item.title">
            <v-list-item-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-action>

            <v-list-item-content>
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </template>
    </v-navigation-drawer>
    <!--Entete de la page Barre verte-->
    <v-app-bar app color="secondary" hide-on-scroll dark>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

      <v-spacer></v-spacer>

      <v-avatar>
        <img src="@/assets/logo.png" alt="logo" />
      </v-avatar>

      <v-spacer> </v-spacer>

      <v-icon v-if="userAuthenticated" @click="logoutUser()">mdi-logout</v-icon>
    </v-app-bar>
    <!---Navigation barre-->
  </div>
</template>

<script>
import { GlobalUserComputed } from "@/utilis/GlobalUserComputed";
import { mapActions } from "vuex";

export default {
  mixins: [GlobalUserComputed],

  data() {
    return {
      drawer: false,
      items: [
        {
          title: "Photos",
          icon: "mdi-folder-multiple-image",
          path: "/uploadflower",
        },
        { title: "Messagerie", icon: "mdi-message-text", path: "/forum" },
      ],
      bottom_items: [
        {
          title: "Aide",
          icon: "mdi-help-circle-outline",
        },
        {
          title: "Paramètre",
          icon: "mdi-cog",
        },
      ],
      links: ["Home", "Contacts", "Settings"],
      mini: true,
    };
  },

  methods: {
    logoutUser() {
      this.clearUserToken();
      if (this.$route.name != "Home") {
        this.$router.push({ name: "Home" });
      }
    },
    ...mapActions(["clearUserToken"]),
  },
};
</script>

<style></style>
