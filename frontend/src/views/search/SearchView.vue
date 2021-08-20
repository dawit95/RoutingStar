<template>
  <v-container style="text-align:center">
    <div class="search-title">
      <h1 style="font-weight:lighter">Search Route</h1>
      <h3 style="color:#C1C8E4; font-weight:lighter">검색하고 싶은 태그를 선택하세요</h3>
    </div>
    
    <div>
      <SearchTagForm/>
    </div>
  </v-container>

</template>

<script>
import SearchTagForm from '@/components/search/SearchTagForm.vue'

export default {
  name: 'SearchView',
  components: {
    SearchTagForm
  }
}
</script>

<style>
.search-title {
  color: #D2FDFF;
  font-family: 'Do Hyeon', sans-serif;
}
</style>