<?php

/** DB - Schema
 * 
 * CREATE TABLE IF NOT EXISTS post (
 *      id INT AUTO_INCREMENT PRIMARY KEY,
 *      title VARCHAR(255) NOT NULL,
 *      description TEXT,
 *      date TIMESTAMP NOT NULL,
 *      autor VARCHAR(255) NOT NULL,
 *      content TEXT,
 *      views INT NOT NULL,
 *      likes INT NOT NULL,
 *      dislikes INT NOT NULL,
 *      readtime INT NOT NULL,
 *      tag VARCHAR(255) NOT NULL
 * );
 * 
 * CREATE TABLE IF NOT EXISTS comments (
 *     id INT AUTO_INCREMENT PRIMARY KEY,
 *     post TEXT,
 *     autor VARCHAR(255) NOT NULL,
 *     comment TEXT,
 *     date TIMESTAMP NOT NULL,
 *     emoji VARCHAR(255)
 * );
 */

setlocale(LC_ALL, 'es_ES.UTF-8', 'es_ES', 'Spanish_Spain', 'Spanish');
date_default_timezone_set('America/Bogota');
$__CURRENTURL = 'http://' . $_SERVER['HTTP_HOST'] . $_SERVER['REQUEST_URI'];

class BlogMaker{

    private $servername = "sql102.hyperphp.com";
    private $username = "hp_32545970";
    private $password = "mamasapo";
    private $dbname = "hp_32545970_blog";

    //private $servername = "localhost";
    //private $username = "root";
    //private $password = "";
    //private $dbname = "blog";
    
    private $conn;
    
    // El constructor de la clase
    public function __construct() {
        $this->conn = new mysqli($this->servername, $this->username, $this->password, $this->dbname);
        if ($this->conn->connect_error) {
          die("Connection failed: " . $this->conn->connect_error);
        }
    }

    public function newPost(){
        $content = mysqli_real_escape_string($this->conn, $_POST['content']);
        $sql = "INSERT INTO `post` (`id`, `title`, `description`, `date`, `autor`, `content`, `views`, `likes`, `dislikes`, `readtime`, `tag`) VALUES (NULL, '".$_POST['title']."',  '".$_POST['description']."', current_timestamp(), '".$_POST['autor']."', '".$content."', '0', '0', '0', ".$_POST['readtime'].", '".$_POST['tag']."');";
        $this->conn->query($sql);
    }

    public function newComment(){
        $comment = mysqli_real_escape_string($this->conn, $_POST['comment']);
        $sql = "INSERT INTO comments (id, post, autor, comment, date, emoji) VALUES (NULL, '".$_POST['post']."', '".$_POST['autor']."', '".$comment."', current_timestamp(), '".$_POST['emoji']."')";
        $this->conn->query($sql);
    }

    // SELECT `id`,`post`,`reply`,`autor`,`comment`,`date`,`emoji` FROM `comments`;

    // -------------------------------------------------------------------------

    public function addLikeToPost($id){
        $sql = "UPDATE `post` SET likes = (likes+1) WHERE id = '$id'";
        $this->conn->query($sql);
    }

    public function addDislikeToPost($id){
        $sql = "UPDATE `post` SET dislikes = (dislikes+1) WHERE id = '$id'";
        $this->conn->query($sql);
    }

    public function addViewToPost($id){
        $sql = "UPDATE `post` SET views = (views+1) WHERE id = '$id'";
        $this->conn->query($sql);
    }

    // -------------------------------------------------------------------------

    public function viewCommentsByPostId($id){
        $sql = "SELECT * FROM `comments` AS C WHERE C.post = '$id' ORDER BY C.date;";
        return $this->getRows($sql);
    }

    public function viewAllPosts(){
        $sql = "SELECT `id`, `title`, `description`, `date`, `autor`, `views`, `likes`, `dislikes`, `readtime`, `tag` FROM post ORDER BY date DESC";
        return $this->getRows($sql);
    }

    public function viewAllTags(){
        $sql = "SELECT DISTINCT tag, COUNT(tag) AS cantidad FROM post GROUP BY tag ORDER BY cantidad DESC LIMIT 15";
        return $this->getRows($sql);
    }

    public function viewMostPopularPost(){
        $sql = "SELECT * FROM post WHERE (DAY(date) = DAY(CURRENT_DATE())) AND (MONTH(date) = MONTH(CURRENT_DATE())) AND (YEAR(date) = YEAR(CURRENT_DATE())) ORDER BY RAND() LIMIT 1";
        return $this->getRows($sql);
    }

    public function viewPost($id){
        $sql = "SELECT * FROM post WHERE id='".$id."'";
        return $this->getRows($sql);
    }

    public function viewPostsByTag($tag){   
        $sql = "SELECT `id`, `title`, `description`, `date`, `autor`, `views`, `likes`, `dislikes`, `readtime`, `tag`  FROM post WHERE tag='".$tag."' ORDER BY date DESC";    
        return $this->getRows($sql);
    }

    // Estos post son los recomendados al visualizar un Post.    
    public function viewRecomendPosts($id){
        $sql = "SELECT `id`, `title`, `description`, `date`, `autor`, `views`, `likes`, `dislikes`, `readtime`, `tag`  FROM post WHERE id != '$id' AND tag=(SELECT tag FROM post WHERE id='".$id."') ORDER BY date DESC LIMIT 6";
        return $this->getRows($sql);
    }

    public function viewSearchedPosts($text){
        $sql = "SELECT `id`, `title`, `description`, `date`, `autor`, `views`, `likes`, `dislikes`, `readtime`, `tag`  FROM post WHERE content LIKE '%$text%' OR title LIKE '%$text%' OR tag LIKE '%$text%' OR autor LIKE '%$text%' OR post.description LIKE '%$text%' ORDER BY date DESC";
        return $this->getRows($sql);
    }

    public function drawPostsList($posts){ // Ends PHP ?> 
        <?php if(sizeof($posts) != 0){  ?>
            <?php for($i = 0; $i < sizeof($posts); $i++){ ?>
                <div class="post">
                    <div class="post_tag"><a href="blog.php?post=<?php echo $posts[$i]["tag"]; ?>"><?php echo $posts[$i]["tag"]; ?></a></div>
                    <div class="post_title"><a href="blog.php?post=<?php echo $posts[$i]["id"]; ?>"><?php echo $posts[$i]["title"]; ?></a></div>
                    <div class="post_description"><?php echo $posts[$i]["description"]; ?></div>
                    <div class="post_date"><i><?php echo date("F j, Y, g:i a", strtotime($posts[$i]["date"])); ?></i></div>
                    <div class="post_autor"><?php echo $posts[$i]["autor"]; ?></div>
                    <div class="post_metrics">
                        <span class="post_views">👁️ <?php echo $posts[$i]["views"]; ?></span>
                        <span class="post_likes">❤️ <?php echo $posts[$i]["likes"]; ?> </span>
                        <span class="post_dislikes">👎 <?php echo $posts[$i]["dislikes"]; ?></span>
                        <span class="post_time">⏱️ <?php echo $posts[$i]["readtime"]; ?></span>
                    </div>
                </div>
            <?php } ?>
        <?php }else{ ?>
            <h4 style="grid-column: span 2;"><i>No se han encontrado resultados relacionados.</i></h4>
        <?php } ?>
    <?php } //Continues PHP

    private function getRows($sql){
        $posts = array();
        $result = $this->conn->query($sql);
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                array_push($posts, $row);
            }
        } 
        return $posts;
    }

    public function drawCommentsList($comments){ // Ends PHP ?> 
        <?php if(sizeof($comments) != 0){  ?>
            <?php for($i = 0; $i < sizeof($comments); $i++){ ?>
                <div id="comment_<?php echo $comments[$i]['id']; ?>" class="comment">
                    <div class="comment_emoji"><?php echo $comments[$i]['emoji']; ?></div>
                    <div class="comment_autor"><b><?php echo $comments[$i]['autor']; ?></b></div>
                    <div class="comment_comment"><b><small><?php echo date("F j, Y, g:i a", strtotime($comments[$i]["date"])); ?></small></b><br>
                        <?php echo $comments[$i]['comment']; ?>
                    </div>
                </div>
            <?php } ?>
        <?php }else{ ?>
            <h4 style="grid-column: span 2;"><i>Se el primero en reaccionar. Dejanos saber tu comentario.</i></h4>
        <?php } ?>
    <?php } //Continues PHP


}
?>