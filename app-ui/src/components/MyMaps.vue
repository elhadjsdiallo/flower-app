<template>
  <div>
    <v-container>
      <v-row>
        <v-col>
          <v-card max-height="300">
            <GmapMap style="height: 300px" :zoom="8" :center="center" ref="map">
              <GmapMarker
                v-for="(marker, index) in markers"
                :key="index"
                :position="marker.latLng"
                :title="marker.title"
                :label="marker.title"
                :clickable="true"
                :draggable="false"
                @click="sendTo(marker.latLng)"
              />
            </GmapMap>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import { createHelpers } from "vuex-map-fields";
import { gmapApi } from "vue2-google-maps";

const { mapFields: mapFlowerFields } = createHelpers({
  getterType: "getFLOWERField",
  mutationType: "updateFLOWERField",
});
export default {
  props: {},
  data() {
    return {
      center: {
        lat: 48.7799511,
        lng: 2.2061946,
      },
      options: {
        mapTypeId: "roadmap",
        zoomControl: true,
        mapTypeControl: false,
        scaleControl: false,
        streetViewControl: false,
        rotateControl: false,
        fullscreenControl: true,
        disableDefaultUi: false,
      },
      markers: [],
      place: null,
    };
  },
  description: `
  In which a random set of points are generated, and
  the bounds of the map are changed to fit the points
  `,
  computed: {
    ...mapFlowerFields(["flowers"]),
    google: gmapApi,
  },
  watch: {
    markers(markers) {
      if (markers.length > 0) {
        const bounds = new google.maps.LatLngBounds();
        for (let m of markers) {
          bounds.extend(m.latLng);
        }
        this.$refs.map.fitBounds(bounds);
      }
    },
  },
  async mounted() {
    let { status } = await this.getAllFlowers();

    if (200 == status) {
      this.loadFlower();
    }
  },
  methods: {
    ...mapActions(["getAllFlowers"]),

    sendTo(latLng) {
      let findFlower = this.flowers.find((f) => {
      
        if (f.latitude == latLng.lat && f.longitude == latLng.lng) {
          return f;
        }
      });

      if (findFlower) {
        this.$router.push({
          name: "FlowerCardDisplayVue",
          params: {
            id: findFlower.id,
          },
        });
      }
    },
    loadFlower() {
      if (this.flowers) {
        this.markers = this.flowers.map((f) => ({
           title: f.description,
          latLng: {
            lat: f.latitude,
            lng: f.longitude,
          },
        }));
        
    
      }
    },
  },
};
</script>
