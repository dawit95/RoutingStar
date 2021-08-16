<template>
  <v-alert
    v-model="alert"
    dismissible
    color="#F4976C"
    border="left"
    elevation="2"
    colored-border
    icon="mdi-hand-heart"
    v-if="getLikeMessage != ''"
  >
      <strong>{{getLikeMessage}}</strong><br/><div class="text-caption">(3초 뒤 메세지가 사라집니다.)</div>
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
    ...mapGetters(['likeMessage']),
    getLikeMessage: function () {
      return this.likeMessage;
    },
  },
  created: function(){
      console.log(this.getLikeMessage + 'is message!!')
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
