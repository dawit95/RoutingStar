<template>
  <div class="commentBox">
    <div>
      <v-row class="form-group Nanum"> 
          <v-text-field dark color="white" label="댓글을 입력해주세요" v-model="WrittingComment"></v-text-field>
          <button class="addCommentBtn moveDown" @click="onClickAddComment">댓글작성</button>
      </v-row>
      
      <div class="commentList Nanum">
        <v-row v-for="(comment, idx) of routeInfo.comments" v-bind:key="idx">
          <div @click="onClickUser(comment.user)">
            <span style="color:white">{{ comment.user.name }}</span>
          </div>
          <span class="mx-3" style="color:white">{{ comment.comment}}</span>
          <!-- 댓글 작성 본인만 삭제 가능하게 -->
          <button class="mx-2" style="color:white" v-if="comment.user.id == jwt[2]" @click="onClickDeleteComment(comment)">X</button>
        </v-row>
      </div>
    </div>
  
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'CommentForm',
  computed: {
    ...mapGetters(['jwt', 'routeInfo', 'newComment', 'deletedComment'])
  },
  data() {
    return {
      WrittingComment: ''
    }
  },
  methods: {
    ...mapActions(['enterUserprofile', 'fetchRouteInfo', 'createComment', 'deleteCommentActions']),

    onClickUser(user) {
      this.enterUserprofile({
        userId: user.id,
        access_token: this.jwt[0],
        jwtId: this.jwt[2]
      })
    },

    onClickAddComment() {
      if (this.WrittingComment) {
        const payload = {
          routeId : this.routeInfo.id,
          access_token : this.jwt[0],
          param : {
            comment: this.WrittingComment,
            id: this.jwt[2]
          }
        } 
        console.log(payload)
        this.createComment(payload)
        this.WrittingComment = ''
      }
    },
    onClickDeleteComment(comment) {
      const payload = {
        commentId : comment.id,
        access_token : this.jwt[0],
      }
      console.log(payload)
      this.deleteCommentActions(payload)
    }
  },
  created() {
    // console.log('여기서도 되나여')
    // console.log(this.$route.params)
    // console.log(this.$route.params.feedId)
    // const routeId = this.$route.params.feedId
    // this.fetchRouteInfoWithComment(routeId)
  },
  watch: {
    newComment: function() {
      this.fetchRouteInfo({
      userId: this.jwt[2],
      routeId: this.routeInfo.id,
      access_token: this.jwt[0]
    })
    },
    deletedComment: function() {
      this.fetchRouteInfo({
      userId: this.jwt[2],
      routeId: this.routeInfo.id,
      access_token: this.jwt[0]
    })
    },
  }
}
</script>

<style scoped>
.Nanum {
  font-family: 'Nanum Gothic Coding', monospace;
}
.moveDown {
  margin-top: 18px;
}
.commentBox {
    width:80%;
    margin:50px;
}
.actionBox .form-group * {
    width:100%;
}
.addCommentBtn {
  width: 67px;
  height: 29px;
  left: 273.96px;
  top: 514px;
  color: #2A355D;
  text-align: center;
  background-color: #FBE8A6;
  border-radius: 30px;  
  filter: drop-shadow(0px 7px 7px rgba(0, 0, 0, 0.25));
  font-size: 0.9em;
  font-weight: bold;
}
.followListImg {
  width: 30px; height: 30px;
  /* object-fit: cover; */
  /* object-position: top; */
  border-radius: 50%;
}
.commentForm {
  border-color: #FBE8A6;
  color: #FBE8A6;
}
</style>
