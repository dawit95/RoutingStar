<template>
  <!-- <v-row justify="center"> -->
    <v-dialog
      v-model="dialog"
      fullscreen
      hide-overlay
      transition="dialog-bottom-transition"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn class="ml-2 moveToRight" color="grey" v-bind="attrs" v-on="on" x-large icon><v-icon>mdi-map-marker-question-outline</v-icon></v-btn>
      </template>
      <v-card>
        <v-toolbar
          dark
          color="#101423"
        >
          <v-btn
            icon
            dark
            @click="dialog = false"
          >
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>👏 User Guide</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
        <user-guide2></user-guide2>
      </v-card>
    </v-dialog>
  <!-- </v-row> -->
</template>

<script>
import UserGuide2 from './UserGuide2.vue';

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
        UserGuide2
    }
  }


</script>

<style scoped>
.theme--dark.v-btn:focus::before {
    opacity: 0.00;
}
.moveToRight {
  left: 10px;
}
</style>