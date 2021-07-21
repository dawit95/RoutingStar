<template>
  <div>
    <Google />
    <Kakao />
  </div>
</template>

<script>
import Google from '@/components/accounts/Google.vue'
import Kakao from '@/components/accounts/Kakao.vue'

export default {
  name: 'LoginView',
  components: {
    Google,
    Kakao,
  },
}
</script>

<style>

</style>