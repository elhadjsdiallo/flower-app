<template>
  <div>
    <v-container>
      <v-row class="mt-15" justify="center" align="center">
        <v-col align-self="end">
          <v-form>
            <v-card>
              <v-card-text>
                <v-text-field
                  color="secondary"
                  v-model="user.email"
                  label="Adresse e-mail"
                ></v-text-field>

                <v-text-field
                  color="secondary"
                  v-model="user.password"
                  label="Mot de passe"
                  :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="show1 = !show1"
                  :type="show1 ? 'text' : 'password'"
                ></v-text-field>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn @click="logUser()" block color="success"
                  >Connexion</v-btn
                >
                <v-spacer></v-spacer>
              </v-card-actions>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn to="/register" block color="primary" class="black--text"
                  >INSCRIPTION</v-btn
                >
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-form>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { GlobalUserComputed } from "@/utilis/GlobalUserComputed";
import { GlobalMethods } from "@/utilis/GlobalMethods";
import { mapActions } from "vuex";

export default {
  mixins: [GlobalUserComputed, GlobalMethods],
  data() {
    return {
      show1: false,
    };
  },
  methods: {
    async logUser() {
      localStorage.removeItem("appToken");
  
      let { status } = await this.login(this.user);
      
      let { status: statusP } = await this.getCurrentUser();
      if (200 != status || 200 != statusP) {
        this.displayBanner({
          message: "Erreur de connexion,username et mot de passe incorrect",
          type: "error",
          activate: true,
        });
      }
    },

    ...mapActions(["getCurrentUser", "login","clearUserToken"]),
  },
};
</script>
