<template>
  <div class="stickybanner">
    <v-alert
      v-model="showError"
      dismissible
      transition="scale-transition"
      :type="getAlertType"
      close-text="Fermer l'alert"
    >
      <div class="text-center">
        {{ errorMessage }}
      </div>
    </v-alert>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  watch: {
    showError(v) {
      if (v) {
        setTimeout(() => {
          if(this.showError)
          {
             this.showError = false;
          }
         
        }, 5000);
      }
    },
  },

  methods: {
    ...mapActions["displayAlert"],
  },
  props: {
    message: {
      type: String,
      default: "erreur",
      required: false,
    },
  },
  computed: {
    ...mapGetters(["getErrorMessage", "getAlertType", "getShowError"]),

    errorMessage: {
      get: function () {
        return this.getErrorMessage;
      },
      set(newMessage) {
        this.displayAlert({ type: "message", data: newMessage });
      },
    },

    showError: {
      get: function () {
        return this.getShowError;
      },
      set(newDisplay) {
        this.$store.dispatch("displayAlert", {
          type: "display",
          data: newDisplay,
        });
      },
    },
  },
  data() {
    return {};
  },
};
</script>
