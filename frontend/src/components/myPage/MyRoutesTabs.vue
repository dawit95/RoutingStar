<template>
  <div>
    <section class="wrapper">
      <ul class="tabs">
        <li class="active">Colors</li>
        <li>Favorite movies</li>
      </ul>

      <ul class="tab__content">
        <li class="active">
          <div class="content__wrapper">
            <h2 class="text-color">Pick a color</h2>
            
            <ul class="colors">
              <li data-color="#2ecc71"></li>
              <li data-color="#D64A4B"></li>
              <li data-color="#8e44ad"></li>
              <li class="active-color" data-color="#46a1de"></li>
              <li data-color="#bdc3c7"></li>
            </ul>
          </div>
        </li>
        
        <li>
          <div class="content__wrapper">
            <h2 class="text-color">Her</h2>
            
            <img src="http://lewihussey.com/codepen-img/her.jpg">
          </div>
        </li>
        
      </ul>
    </section>

  </div>
</template>

<script>
export default {

}
</script>

<style>
*{
	box-sizing: border-box;
	padding: 0;
	margin: 0;
}

body{
	font-family: 'Open Sans', sans-serif;
	color: rgb(80, 85, 90);
	padding: 100px 20px;
}

.bg-color{
	background-color: #46a1de;
	transition-duration: .5s;
}

.text-color{
	color: #46a1de;
	transition-duration: .5s;
}

.wrapper{
	min-width: 600px;
	max-width: 900px;
	margin: 0 auto;
}

.tabs{
	display: table;
	table-layout: fixed;
	width: 100%;
	-webkit-transform: translateY(5px);
	transform: translateY(5px);
}	

li{
transition-duration: .25s;
display: table-cell;
list-style: none;
text-align: center;
padding: 20px 20px 25px 20px;
position: relative;
overflow: hidden;
cursor: pointer;
color: white;
}

li:before{
  z-index: -1;
  position: absolute;
  content: "";
  width: 100%;
  height: 120%;
  top: 0;
  left: 0;
  background-color: rgba(255, 255, 255, .3);
  -webkit-transform: translateY(100%);
  transform: translateY(100%);
  transition-duration: .25s;
  border-radius: 5px 5px 0 0;
}

li:hover{
    -webkit-transform: translateY(70%);
    transform: translateY(70%);
}
  
  li.active{
  color: rgb(80, 85, 90);  
}
li.active:before{
  transition-duration: .5s;
  background-color: white;
  -webkit-transform: translateY(0);
  transform: translateY(0);
}

.tab__content{
	background-color: white;
	position: relative;
	width: 100%;
	border-radius: 5px;
}

</style>