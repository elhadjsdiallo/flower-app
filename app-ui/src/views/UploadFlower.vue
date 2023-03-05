<template>
  <div>
    <v-container>
      <v-form>
        <v-card>
          <v-card-title> Ajouter vos plantes favorites </v-card-title>
          <v-card-text>
            <v-row>
              <v-col cols="12">
                <v-file-input
                v-model="userFlower.flowerImage"
                  color="secondary"
                  label="Ajouter une photo"
                  filled
                  prepend-icon="mdi-camera"
              
                ></v-file-input>
              </v-col>
              <v-col cols="12">
                <v-textarea
                  v-model="userFlower.description"
                  color="secondary"
                  name="description"
                  filled
                  label="Ajouter une description"
                  auto-grow
                  value="Plante aquatique"
                ></v-textarea>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="userFlower.adresse"
                  color="secondary"
                  outlined
                  label="Entrer la localistion de la plante"
                >
                </v-text-field>
              </v-col>
            </v-row>
          </v-card-text>
          <v-card-actions>
            <v-btn @click="sendFlower()" block color="secondary">Ajouter</v-btn>
          </v-card-actions>
        </v-card>
      </v-form>
    </v-container>
  </div>
</template>

<script>
import { GlobalMethods } from "@/utilis/GlobalMethods";
import { GlobalUserComputed } from "@/utilis/GlobalUserComputed";

import { mapActions } from "vuex";
import { createHelpers } from "vuex-map-fields";

const { mapFields: mapFlowerFields } = createHelpers({
  getterType: "getFLOWERField",
  mutationType: "updateFLOWERField",
});

export default {
  name: "UploadFlower",
  mixins: [GlobalUserComputed, GlobalMethods],
  data() {
    return {
      franceGpsPool: [
        {
          latitude: 48.85341,
          longitude: 2.3488,
        },
        {
          latitude: 43.29695,
          longitude: 5.38107,
        },
        {
          latitude: 45.74846,
          longitude: 4.84671,
        },
        {
          latitude: 43.60426,
          longitude: 1.44367,
        },
        {
          latitude: 43.70313,
          longitude: 7.26608,
        },
        {
          latitude: 47.21725,
          longitude: -1.55336,
        },
        {
          latitude: 48.58392,
          longitude: 7.74553,
        },
      ],
    };
  },
  computed: {

...mapFlowerFields(["userFlower", "dialog"]),
},
  methods: {
      ...mapActions(["uploadFlowerImage"]),
      async sendFlower() {
        let index = Math.floor(Math.random() * 7);

        let position = this.franceGpsPool[index];
        console.log(position);
        let { status } = await this.uploadFlowerImage({
          userId: this.user.id,
          userFlower: this.userFlower,
          position: position,
        });

        if (200 == status) {
          this.displayBanner({
            message: "Fleur ajoutée",
            type: "success",
            activate: true,
          });
        }
        else if(401==status)
        {
          this.displayBanner({
            message: "Votre session a expirée",
            type: "error",
            activate: true,
          }); 
          this.userAuthenticated=false
          this.$router.push({
            name:'Home'
          })
        }
      },
    },

};
</script>
