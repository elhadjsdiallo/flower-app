import { createHelpers } from "vuex-map-fields";
const { mapFields: mapUserFields } = createHelpers({
  getterType: "getUSERField",
  mutationType: "updateUSERField",
});

export const GlobalUserComputed = {
  computed: {
    ...mapUserFields({
      user: "user",
      userAuthenticated:'userAuthenticated'
    }),
  },
};
