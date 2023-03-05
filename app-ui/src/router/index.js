import Vue from "vue";
import VueRouter from "vue-router";

import HomeView from "../views/HomeView.vue";
import FlowerDisplay from "../views/FlowerDisplay.vue";
import ForumDisplay from "../views/ForumDisplay.vue";
import InscriptionDisplay from "@/views/InscriptionDisplay.vue";
import FlowerCardDisplayVue from "@/components/FlowerCardDisplay.vue";
import TermsOfService from "@/views/TermsOfService.vue"
import UploadFlower from "@/views/UploadFlower.vue";
import PrivatePolicy from "@/views/PrivatePolicy.vue"
import store from "@/store";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: HomeView,
    alias: "/home",
  },
  {
   
    path: "/uploadflower",
    name: "FlowerDisplay",
    component: FlowerDisplay,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/forum",
    name: "ForumDisplay",
    component: ForumDisplay,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/register",
    name: "InscriptionDisplay",
    component: InscriptionDisplay,
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: "/flower",
    name: "UploadFlower",
    component: UploadFlower,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/displayflower/:id",
    name: "FlowerCardDisplayVue",
    component: FlowerCardDisplayVue,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/terms-service",
    name: "TermsOfService",
    component: TermsOfService,
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: "/private-policy",
    name: "PrivatePolicy",
    component: PrivatePolicy,
    meta: {
      requiresAuth: false,
    },
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});
router.beforeEach((to, from, next) => {
  window.scrollTo(0, 0);
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (store.getters.isAuthenticated) {
      next();
      return;
    }
    //next();
    if (from.name != "Home") {
      router.push({
        name: "Home",
      });
    }
  } else {
    next();
  }
});
export default router;
