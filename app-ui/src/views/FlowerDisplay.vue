<template>
  <div>
    <v-container>
      <v-row>
        <v-col>
          <h3>
            <v-icon left>mdi-flower</v-icon>
            Plantes
          </h3>
        </v-col>
      </v-row>
    </v-container>
    <v-container>
      <v-row>
        <v-col cols="4" v-for="(flower, index) in userListFlowers" :key="index">
          <v-card>
            <v-img
              height="80px"
              contain
              :src="publicUrl + '' + flower.imagePath"
              @click="sendTo(flower.id)"
            >
            </v-img>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="success" icon
                ><v-icon @click="removeFlower(flower.id)" color="secondary"
                  >mdi-delete</v-icon
                ></v-btn
              >

              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
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
  name: "FlowerDisplay",
  components: {},

  mixins: [GlobalUserComputed, GlobalMethods],
  computed: {
    ...mapFlowerFields(["userListFlowers"]),
    publicUrl() {
      return process.env.VUE_APP_PUBLIC_URL;
    },
  },
  async mounted() {
    this.getUserflowers();
  },
  methods: {
    ...mapActions(["getUserFlower", "deleteFlower"]),
    async removeFlower(flowerId) {
      let { status } = await this.deleteFlower({ flowerId: flowerId });
      if (200 == status) {
        this.displayBanner({
          message: "Votre publication a été supprimer",
          type: "success",
          activate: true,
        });
        this.getUserflowers();
      } else {
        this.displayBanner({
          message: "Echec de la suppression",
          type: "error",
          activate: true,
        });
      }
    },

    sendTo(flowerId) {
      this.$router.push({
        name: "FlowerCardDisplayVue",
        params: {
          id: flowerId,
        },
      });
    },

    async getUserflowers() {
      let { status } = await this.getUserFlower({
        userId: this.user.id,
      });
      if (200 != status) {
        this.displayBanner({
          message: "Impossible de recuperer les images utilisateurs",
          type: "error",
          activate: true,
        });
      }
    },
  },

  data() {
    return {};
  },
};
</script>

<style></style>
