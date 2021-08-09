<template>
  <div>
    {{ feed }}
  </div>
</template>

<script>
export default {
  name: 'HomeDetailView',
  props: {
    feed: {
      type: Object,
    }
  }

}
</script>

<style>

</style>