<template>
  <!-- <v-row justify="center"> -->
    <v-dialog
      v-model="dialog"
      fullscreen
      hide-overlay
      transition="dialog-bottom-transition"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn color="primary" dark v-bind="attrs" v-on="on" icon><v-icon>mdi-lightbulb-multiple-outline</v-icon></v-btn>
      </template>
      <v-card>
        <v-toolbar
          dark
          color="primary"
        >
          <v-btn
            icon
            dark
            @click="dialog = false"
          >
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>Settings</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
        <user-guide></user-guide>
      </v-card>
    </v-dialog>
  <!-- </v-row> -->
</template>

<script>
import UserGuide from './UserGuide.vue';

  export default {
    data () {
      return {
        dialog: false,
        notifications: false,
        sound: true,
        widgets: false,
      }
    },
    components: {
        UserGuide
    }
  }


</script>

<style scoped>
.theme--dark.v-btn:focus::before {
    opacity: 0.00;
}


</style>