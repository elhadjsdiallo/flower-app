<template>
  <div>
    <v-container>
      <v-row class="mt-15" justify="center" align="center">
        <v-col align-self="end">
          <v-form ref="form" v-model="valid" lazy-validation>
            <v-card>
              <v-card-text>
                <v-text-field
                  v-model="user.firstName"
                  label="Prenom"
                  color="secondary"
                  :rules="nameRules"
                ></v-text-field>
                <v-text-field
                  v-model="user.lastName"
                  label="Nom"
                  color="secondary"
                  :rules="nameRules"
                ></v-text-field>
                <v-text-field
                  v-model="user.email"
                  label="Adresse e-mail"
                  color="secondary"
                  :rules="emailRules"
                ></v-text-field>

                <v-text-field
                  v-model="user.password"
                  label="Mot de passe"
                  color="secondary"
                  :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="show1 = !show1"
                  :type="show1 ? 'text' : 'password'"
                  :rules="passwordRules"
                ></v-text-field>
                <v-text-field
                  v-model="user.passwordConfirmation"
                  label="Confirmez votre mot de passe"
                  color="secondary"
                  :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="show2 = !show2"
                  :type="show2 ? 'text' : 'password'"
                  :rules="passwordRules"
                ></v-text-field>
                <v-checkbox :rules="checkboxRules" color="secondary">
                  <template v-slot:label>
                    <div>
                      j'accepte les
                      <router-link class="green--text" to="/terms-service"
                        >termes du Service</router-link
                      >
                      et la politique privée
                      <router-link class="green--text" to="/private-policy"
                        >politique privée</router-link
                      >*
                    </div>
                  </template>
                </v-checkbox>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  :disabled="!valid"
                  @click="registerUser()"
                  block
                  color="success"
                  >Inscription</v-btn
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
import { GlobalMethods } from "@/utilis/GlobalMethods";
import { GlobalUserComputed } from "@/utilis/GlobalUserComputed";
import { mapActions } from "vuex";

export default {
  name: "InscriptionDisplay",
  mixins: [GlobalUserComputed, GlobalMethods],
  data() {
    return {
      show1: false,
      show2: false,
      valid: true,
      nameRules: [(v) => !!v || "Le champ est obligatoire"],
      emailRules: [
        (v) => !!v || "E-mail est obligation",
        (v) => /.+@.+\..+/.test(v) || "E-mail doit être validé",
      ],
      passwordRules: [
        (v) => !!v || "Password est obligatoire",
        (v) =>
          /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])(?=.{8,})/.test(v) ||
          "Password est invalid",
      ],
      checkboxRules: [
        (v) => !!v || "Vous devez accepter les conditions  pour continuer!",
      ],
    };
  },
  methods: {
    ...mapActions(["createUser"]),
    async registerUser() {
      //validate

      let intValid = await this.$refs.form.validate();
      intValid = this.user.passwordConfirmation == this.user.password;
      if (!intValid) return;

      let response = await this.createUser(this.user);

      if (response.status == 200) {
        this.$router.push({ name: "Home" });
      } else {
        this.displayBanner({
          message: "Utilisateur déjà enregistrer",
          type: "error",
          activate: true,
        });
      }
    },
  },
};
</script>
