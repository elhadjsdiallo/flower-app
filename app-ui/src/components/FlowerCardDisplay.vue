<template>
  <div>
    <v-card v-if="flowerToDisplay" class="mx-auto my-12" max-width="374">
      <template slot="progress">
        <v-progress-linear
          color="deep-purple"
          height="10"
          indeterminate
        ></v-progress-linear>
      </template>

      <v-img height="250" :src="publicUrl + '' + flowerToDisplay.imagePath"></v-img>

      <v-card-title>{{flowerToDisplay.imageName}}</v-card-title>

      <v-card-text>
        <v-row align="center" class="mx-0">
          <v-rating
            :value="4.5"
            color="amber"
            dense
            half-increments
            readonly
            size="14"
          ></v-rating>

          <div class="grey--text ms-4">4.5</div>
        </v-row>

        <div class="mt-5">
          {{ flowerToDisplay.description }}
        </div>
        <v-chip
          ><v-icon color="secondary" small start>mdi-map-marker</v-icon>
          {{ flowerToDisplay.adresse }}</v-chip
        >
      </v-card-text>

      <v-divider class="mx-4"></v-divider>

      <v-card-actions>
        <v-list-item class="grow">
          <v-list-item-avatar color="grey darken-3">
            <v-img
              class="elevation-6"
              alt=""
              src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
            ></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title
              >{{ flowerToDisplay.user.firstName }} {{ flowerToDisplay.user.lastName }}</v-list-item-title
            >
          </v-list-item-content>

          <v-row align="center" justify="end">
            <v-icon class="mr-1"> mdi-heart </v-icon>
            <span class="subheading mr-2">256</span>
            <span class="mr-1">·</span>
            <v-icon class="mr-1"> mdi-share-variant </v-icon>
            <span class="subheading">45</span>
          </v-row>
        </v-list-item>
        
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
import { GlobalUserComputed } from "@/utilis/GlobalUserComputed";
import { createHelpers } from "vuex-map-fields";
import { mapActions } from 'vuex';
import { GlobalMethods } from "@/utilis/GlobalMethods";



const { mapFields: mapFlowerFields } = createHelpers({
  getterType: "getFLOWERField",
  mutationType: "updateFLOWERField",
});

export default {
  mixins: [GlobalUserComputed,GlobalMethods],
  data() {
    return {
      flower: {},
    };
  },
  computed: {
    ...mapFlowerFields(["flowerToDisplay"]),
    publicUrl() {
      return process.env.VUE_APP_PUBLIC_URL;
    },
  },
  async mounted() {
    let position = this.$route.params.id;

    let { status } = await this.getFlower({
      flowerId: position,
    });

    if (200 != status) {
        this.displayBanner({
          message: "Failed to fech flower",
          type: "error",
          activate: true,
        });

    }
  },
  methods: {
    ...mapActions([
        "getFlower"
    ])
  },
};
</script>

