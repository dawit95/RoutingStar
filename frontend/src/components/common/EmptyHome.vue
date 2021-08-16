<template>
  <div class="empty-alert mx-auto mt-3 animate__animated animate__shakeY animate__slower">
    <div class="emplty-alert-text">
      <h3 style="font-weight:lighter">앗! 아직 작성한 루트가 없군요!</h3>
      <h3 style="font-weight:lighter">아래를 눌러 포스트를 작성해볼까요?</h3>
      <v-icon style="color:#F4976C" x-large>
        mdi-arrow-down
      </v-icon>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EmptyHome'
}
</script>

<style>
.empty-alert {
  height: 200px;
  width: 300px;
  background-color: aliceblue;
  position: relative;
  display: flex;
  justify-content: center;
  border-radius: 56px;
  background: #2A355D;
  box-shadow: inset 5px 5px 11px #1a213a,
              inset -5px -5px 11px #3a4980;
}
.emplty-alert-text {
  font-family: 'Do Hyeon', sans-serif;
  position: absolute;
  text-align: center;
  top: 40%;
  color: #D2FEFF;
}
</style>