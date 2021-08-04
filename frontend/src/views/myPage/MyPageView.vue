<template>
  <div>

    <ProfileInfo/>
    <MyRoutes/>

  </div>
</template>

<script>
import ProfileInfo from '@/components/myPage/ProfileInfo.vue'
import MyRoutes from '@/components/myPage/MyRoutes.vue'

export default {
  name: 'MyPage',
  components: {
    ProfileInfo,
    MyRoutes,
  },
}
</script>

<style>

</style>