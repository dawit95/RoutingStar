<!--https://github.com/Abiola-Farounbi/Landing-Page-VUE/blob/master/src/App.vue-->
<template>
<v-col cols="12" sm="1" md="1">
  <main id="app">
    <section class='intro'>
      <div class='introDescription' data-aos="zoom-in"
     data-aos-duration="1500"
     data-aos-delay='500' >
     
         <div class="text-caption">당신의 발자취가 별이 되어 떠있는 곳</div>
         <p> RoutingStar </p>
      </div>
        <img  class='introImage' src='https://res.cloudinary.com/dsderm9xw/image/upload/v1622028117/3D%20Illustrations/3d-illustration4_usckww.png' alt='3d illustration of boy with a flag'/>

    </section>

     <section class='guide1'>
       <img class='guide1Image' src='https://res.cloudinary.com/dsderm9xw/image/upload/v1622028085/3D%20Illustrations/3d-illustration2_ykuua3.png' 
       alt='3d illustration of boy sitting with legs crossed'
       data-aos="slide-down"
     data-aos-ease = 'ease'
      data-aos-duration="1500"
     data-aos-delay='500'/>
      <div class='guide1Description'
      data-aos="slide-up"
     data-aos-ease = 'ease'
      data-aos-duration="1500"
     data-aos-delay='1000'>
        <h1> 정보를 얻을래! </h1>
        <br>
         <div>

         </div>
      </div>
        

    </section>

     <section class='guide2'>
      <div class='guide2Description'
      data-aos="slide-down"
        data-aos-ease = 'ease'
         data-aos-duration="1500"
        data-aos-delay='1000'>
                <img class='guide2Image' src='https://res.cloudinary.com/dsderm9xw/image/upload/v1622028057/3D%20Illustrations/3d-illustration1_j06j0a.png' 
        alt='3d illustration of boy with laptop sitting'
        data-aos="slide-up"
         data-aos-duration="1500"
        data-aos-ease = 'ease'
        data-aos-delay='1000'
        />
            <h1> 공유할래! </h1>
         <div>

         </div>
      </div>


    </section>


     <section class='guide3'>
      <div class='guide3Description'
      data-aos="slide-down"
        data-aos-ease = 'ease'
         data-aos-duration="1500"
        data-aos-delay='1000'>
                <img class='guide3Image' src='https://superscene.pro/images/section-2-image-6@2x.png' width="282.56px" height="300px"
        alt='3d illustration of boy with laptop sitting'
        data-aos="slide-up"
         data-aos-duration="1500"
        data-aos-ease = 'ease'
        data-aos-delay='1000'
        />
            <h1> 탐나는데? </h1>
         <div>

         </div>
      </div>

     </section>
    
  
  <footer>
    Developed by RoutingStar
  </footer>
  </main>
</v-col>
</template>



<!-- Use preprocessors via the lang attribute! e.g. <style lang="scss"> -->
<style>
@import url('https://fonts.googleapis.com/css2?family=Palette+Mosaic&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
*{
  margin:0;
  padding:0;
  font-family: 'Nanum Gothic', sans-serif;
}

.intro{
  display:flex;
  background: #101423;
  justify-content: space-evenly;
  padding-top:80px;
  /* height: 500px; */
}
.introDescription {
  font-size:20px;
  color:#FBE8A6;
  padding-top:120px;
}
.introDescription p{
  font-size:40px;
  color:#FBE8A6;
  font-weight: 900;
  font-family: 'Palette Mosaic', cursive;
}

.guide1{
  display:flex;
  background: #FBE8A6;
  justify-content: space-evenly;
  padding-top:80px;
  overflow:hidden;
  height: 500px;
}
.guide1Description h1{
  color:#101423;
  padding: 120px 0px 20px 0px;
  font-size: 48px;
}
.guide1Description div{
  background:#fff3e2 ;
  color:#101423;
  font-size:20px;
  padding:10px;
  border-radius:10px;
}


.guide2{
  display:flex;
  background: #fff3e2;
  justify-content: space-evenly;
  padding-top:80px;
   overflow:hidden;
   height: 500px;
}
.guide2Description h1{
  font-size:48px;
  color:#632b2b;
  padding: 120px 0px 20px 0px;
}
.guide2Description div{
  background:#ffd4a9 ;
  color:#632b2b;
  font-size:20px;
  padding:10px;
  border-radius:10px;
}

.guide3{
  display:flex;
  background: #101423;
  justify-content: space-evenly;
  padding-top:80px;
   overflow:hidden;
   height: 500px;
}
.guide3Description h1{
  font-size:48px;
  color:#fff3e2;
  padding: 120px 0px 20px 0px;
}
.guide3Description div{
  background:#ffd4a9 ;
  color:#fff3e2;
  font-size:20px;
  padding:10px;
  border-radius:10px;
}

footer{
  background: #101423;
  color:#632b2b;
  padding:10px;
  text-align: right;
  font-weight: 900;
}
</style>

<script>
import AOS from 'aos'
import 'aos/dist/aos.css'
  
export default {
    created () {
    AOS.init()
}
}
</script>