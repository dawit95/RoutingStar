<template id="falling">
  <div class="container">
    <div class="loader">
      <span>
        <img class="routePng" src="../../assets/routes/1.png" alt="">
      </span>
      <span>
        <img class="routePng" src="../../assets/routes/2.png" alt="">
      </span>
      <span>
        <img class="routePng" src="../../assets/routes/3.png" alt="">
      </span>
      <span>
        <img class="routePng" src="../../assets/routes/4.png" alt="">
      </span>
      <span>
        <img class="routePng" src="../../assets/routes/5.png" alt="">
      </span>
      <span>
        <img class="routePng" src="../../assets/routes/6.png" alt="">
      </span>
      <span>
        <img class="routePng" src="../../assets/routes/7.png" alt="">
      </span>
      <span>
        <img class="routePng" src="../../assets/routes/1.png" alt="">
      </span>
      <span>
        <img class="routePng" src="../../assets/routes/2.png" alt="">
      </span>
      
    </div>
  </div>
</template>

<script>
export default {
  name: 'FallingImg'
}
</script>

<style scoped>
body{
  margin: 0;
}

#app{
  height: 100vh;
  width: 100vw;

}

.routePng {
  width: 130px;
}

.container {
  top: 0;
  /* background: #fff; */
  right: 0;
  bottom: 0;
  position: absolute;
  left: 0;
  overflow: hidden;
}

.loader {
  text-align: center;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.loader span {
  width: 128px;
  height: 128px;
  /*   margin: -280px 40px 54px  -34px; */
  /* background: url("../../assets/routes/1.png"); */
  opacity: 0;
  -webkit-animation: loader 10s infinite linear;
  -moz-animation: loader 10s infinite linear;
  animation: loader 10s infinite linear;
}

.loader span:nth-child(5n+5) {
  -webkit-animation-delay: 0.7s;
  -moz-animation-delay: 0.7s;
  animation-delay: 0.7s;
}

.loader span:nth-child(3n+2) {
  -webkit-animation-delay: 0.9s;
  -moz-animation-delay: 0.9s;
  animation-delay: 0.9s;
}

.loader span:nth-child(2n+5) {
  -webkit-animation-delay: 1.0s;
  -moz-animation-delay: 1.0s;
  animation-delay: 1.0s;
}

.loader span:nth-child(3n+10) {
  -webkit-animation-delay: 1.2s;
  -moz-animation-delay: 1.2s;
  animation-delay: 1.2s;
}

.loader span:nth-child(7n+2) {
  -webkit-animation-delay: 1.5s;
  -moz-animation-delay: 1.5s;
  animation-delay: 1.5s;
}

.loader span:nth-child(4n+5) {
  -webkit-animation-delay: 1.7s;
  -moz-animation-delay: 1.7s;
  animation-delay: 1.7s;
}

.loader span:nth-child(3n+7) {
  -webkit-animation-delay: 2s;
  -moz-animation-delay: 2s;
  animation-delay: 2s;
}

.loader span:nth-child(3n+8) {
  -webkit-animation-delay: 2.1s;
  -moz-animation-delay: 2.1s;
  animation-delay: 2.1s;
}


@keyframes loader {
  0% {
    width: 128px;
    height: 128px;
    opacity: 0;

    -webkit-transform: translate(0, 0vh) rotateZ(0deg);
  }
  25% {
    width: 128px;
    height: 128px;
    opacity: 1;

    -webkit-transform: translate(0, 25vh) rotateZ(90deg);
  }
  75% {
    width: 128px;
    height: 128px;
    opacity: 1;

    -webkit-transform: translate(5vw, 75vh) rotateZ(270deg);
  }
  100% {
    width: 128px;
    height: 128px;
    opacity: 0;

    -webkit-transform: translate(10vw, 100vh) rotateZ(360deg);
  }
}

@-webkit-keyframes loader {
  0% {
    width: 128px;
    height: 128px;
    opacity: 0;

    -webkit-transform: translate(0, 0vh) rotateZ(0deg);
  }
  25% {
    width: 128px;
    height: 128px;
    opacity: 1;

    -webkit-transform: translate(0, 25vh) rotateZ(90deg);
  }
  75% {
    width: 128px;
    height: 128px;
    opacity: 1;

    -webkit-transform: translate(5vw, 75vh) rotateZ(270deg);
  }
  100% {
    width: 128px;
    height: 128px;
    opacity: 0;

    -webkit-transform: translate(10vw, 100vh) rotateZ(360deg);
  }
}

@-moz-keyframes loader {
  0% {
    width: 128px;
    height: 128px;
    opacity: 0;

    -webkit-transform: translate(0, 0vh) rotateZ(0deg);
  }
  25% {
    width: 128px;
    height: 128px;
    opacity: 1;

    -webkit-transform: translate(0, 25vh) rotateZ(90deg);
  }
  75% {
    width: 128px;
    height: 128px;
    opacity: 1;

    -webkit-transform: translate(5vw, 75vh) rotateZ(270deg);
  }
  100% {
    width: 128px;
    height: 128px;
    opacity: 0;

    -webkit-transform: translate(10vw, 100vh) rotateZ(360deg);
  }
}

</style>