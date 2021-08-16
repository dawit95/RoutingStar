<template>
  <v-alert
    v-model="alert"
    dismissible
    color="#94DADC"
    border="left"
    elevation="2"
    colored-border
    icon="mdi-bookmark-check"
    v-if="getStoreMessage != ''"
  >
      <strong>{{getStoreMessage}}</strong> <br/> <div class="text-caption">(3초 뒤 메세지가 사라집니다.)</div>
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
    ...mapGetters(['storeMessage']),
    getStoreMessage: function () {
      return this.storeMessage;
    },
  },
  created: function(){
      console.log(this.getStoreMessage + 'is message!!')
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
