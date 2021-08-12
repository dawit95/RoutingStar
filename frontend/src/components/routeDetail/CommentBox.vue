<template>
  <div class="commentBox">
    
    <div>
      <ul class="commentList">
          <li>
              <div class="commenterImage">
                <img src="http://placekitten.com/50/50" />
              </div>
              <div class="commentText">
                  <p class="">Hello this is a test comment.</p> <span class="date sub-text">on March 5th, 2014</span>
              </div>
          </li>
      </ul>

      <form class="form-inline" role="form">
          <div class="form-group">
              <input class="form-control" type="text" placeholder="Your comments" />
          </div>
          <div class="form-group">
              <button class="btn btn-default">Add</button>
          </div>
      </form>
    </div>
  
  </div>
</template>

<script>
export default {
  name: 'CommentForm',
}
</script>

<style>
.commentBox {
    width:320px;
    border:1px solid #bbb;
    margin:50px;
}
.actionBox .form-group * {
    width:100%;
}
</style>