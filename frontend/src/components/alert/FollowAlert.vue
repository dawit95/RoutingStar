<template>
  <v-alert
    v-model="alert"
    dismissible
    color="#F9DF88"
    border="left"
    elevation="2"
    colored-border
    icon="mdi-account-heart"
    v-if="getAlertMessage != ''"
  >
    <strong>{{ this.getAlertMessage }}</strong> <br/> <div class="text-caption">(3초 뒤 메세지가 사라집니다.)</div>
  </v-alert>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  data() {
    return {
        alert: true,
        overlay: false
    };
  },
  computed: {
    ...mapGetters(['alertMessage']),
    getAlertMessage: function () {
      return this.alertMessage;
    },
  }
};
</script>

<style scoped>
.v-alert {
    position: fixed;
    width: 100%;
    z-index: 100;
}

</style>
