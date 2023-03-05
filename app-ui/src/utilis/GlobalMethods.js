import { mapActions } from "vuex";

export const GlobalMethods = {
  methods: {
    ...mapActions(["displayBanner"]),
  },
};
