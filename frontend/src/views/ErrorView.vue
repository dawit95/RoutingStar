<template>
  <div>
    <div class="headprop mt-8">
      <p class="head">Error Page</p>
      
    </div>
    <div class="box">
      <span class="d-flex justify-center">
        <img class="logo-image bounce-in-top" src="https://routingstar-photo-album.s3.ap-northeast-2.amazonaws.com/assets/error.jpg.png" alt="">
      </span>
    </div>
    <div>
      <p class="sub-head">404 Not Found</p>
    </div>
    <div class="d-flex justify-center">
      <v-btn elevation="2" color="secondary" @click="this.moveToHome">홈으로 돌아가기!</v-btn>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ErrorView',
  data() {
    return {
      avatar: null,
    }
  },
  methods: {
    moveToHome() {
      this.$router.push({ name: "HomeView" })
    }
  }
}
</script>

<style scoped>
.headprop {
  height: 120px;
}
.head {
  text-align: center;
  color: white;
  font-size: 70px;
  font-family: 'Montserrat', sans-serif;
}
.sub-head {
  text-align: center;
  color: white;
  font-size: 35px;
  font-family: 'Montserrat', sans-serif;
}
.box {
  position: relative;
  margin-bottom: 30px;
}
.logo-image {
  width: 190px;
  height: 190px;
  object-fit: cover;
  object-position: top;
  border-radius: 50%;
  /* position: absolute; */
  top: 2%;
  /* left: 19.3%; */
}
.bounce-in-top {
	animation: bounce-in-top 1.1s both;
}
@keyframes bounce-in-top {
  0% {
    transform: translateY(-500px);
    animation-timing-function: ease-in;
    opacity: 0;
  }
  38% {
    transform: translateY(0);
    animation-timing-function: ease-out;
    opacity: 1;
  }
  55% {
    transform: translateY(-65px);
    animation-timing-function: ease-in;
  }
  72% {
    transform: translateY(0);
    animation-timing-function: ease-out;
  }
  81% {
    transform: translateY(-28px);
    animation-timing-function: ease-in;
  }
  90% {
    transform: translateY(0);
    animation-timing-function: ease-out;
  }
  95% {
    transform: translateY(-8px);
    animation-timing-function: ease-in;
  }
  100% {
    transform: translateY(0);
    animation-timing-function: ease-out;
  }
}

</style>