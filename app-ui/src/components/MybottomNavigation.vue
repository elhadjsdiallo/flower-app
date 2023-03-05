<template>
  <div>
    <!-- <div id="create" class="text-end mb-15">
      <v-card-text style="height: 100px; position: relative">

      </v-card-text>

    </div> -->
    <v-footer app>
      <v-speed-dial
        v-model="fab"
        :top="top"
        :bottom="bottom"
        :right="right"
        :left="left"
        :direction="direction"
        :open-on-hover="hover"
        :transition="transition"
      >
        <template v-slot:activator>
          <v-btn v-model="fab" color="secondary" dark fab>
            <v-icon v-if="fab"> mdi-close </v-icon>
            <v-icon v-else> mdi-plus </v-icon>
          </v-btn>
        </template>

        <v-btn fab dark small color="primary">
          <v-icon color="grey">mdi-head-cog-outline</v-icon>
        </v-btn>
      </v-speed-dial>
    </v-footer>

    <v-bottom-navigation app background-color="primary" color="secondary" grow>
      <v-btn to="/flower" value="flower">
        <span>Plantes</span>

        <v-icon>mdi-flower</v-icon>
      </v-btn>

      <v-btn to="/" value="localisation">
        <span>Localisation</span>

        <v-icon>mdi-crosshairs-gps</v-icon>
      </v-btn>

      <v-btn to="/forum" value="forum">
        <span>Forum</span>

        <v-icon>mdi-forum</v-icon>
      </v-btn>
    </v-bottom-navigation>
  </div>
</template>

<script>
export default {
  data: () => ({
    direction: "top",
    fab: false,
    fling: false,
    hover: false,
    tabs: null,
    top: false,
    right: true,
    bottom: true,
    left: false,
    transition: "slide-y-reverse-transition",
  }),
};
</script>

<style>
.v-speed-dial {
  position: absolute;
}
</style>
