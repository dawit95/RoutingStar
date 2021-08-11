<template>
  <v-app>
    <h1>Search Page</h1>
    <h2>원하는 태그를 선택하세요</h2>
    <div>
      <SearchTagForm/>
    </div>
  </v-app>

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

</style>