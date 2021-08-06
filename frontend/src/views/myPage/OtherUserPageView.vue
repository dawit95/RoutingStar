<template>
  <div>
    <h1>로그인한 유저가 보는 남의 페이지입니다.</h1>
    <ProfileInfo/>
    <OtherUserRoutesTab/>

  </div>
</template>

<script>
import ProfileInfo from '@/components/myPage/ProfileInfo.vue'
import OtherUserRoutesTab from '@/components/myPage/OtherUserRoutesTab.vue'

export default {
  name: 'MyPage',
  components: {
    ProfileInfo,
    OtherUserRoutesTab,
  },
}
</script>

<style>

</style>