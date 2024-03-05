<?php
include_once('./scripts/BlogMaker.php');
$blogMaker = new BlogMaker();

// Check new Post
if(isset($_POST['new_post']) and isset($_POST['password']) and $_POST['password'] == ('XDRTFC'.date('d')) ){
    $blogMaker->newPost();
}

// Check new Comment
if(isset($_POST['new_comment'])){
    $blogMaker->newComment();
}

// Check like and dislike
if(isset($_GET['post']) and is_numeric($_GET['post'])){
    if(isset($_GET['like']) and empty($_GET['like'])){ 
        $blogMaker->addLikeToPost($_GET['post']); 
        echo "<script>window.close();</script>";
        die(); exit();
    }
    if(isset($_GET['dislike']) and empty($_GET['dislike'])){ 
        $blogMaker->addDislikeToPost($_GET['post']); 
        echo "<script>window.close();</script>";
        die(); exit();
    }
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" type="image/jpg" href="./img/logoJDSLJUAN.svg" />
    <title>Personal Micro-Blog</title>
    <link rel="stylesheet" type="text/css" href="./css/blog.css">
    <!-- SEO -->
    <meta name="description" content="Colombia, Saravena, Arauca, noticias, opinión, tendencias tecnológicas, tendencias económicas, tendencias políticas, educación, recursos web, blogs, blog">
    <meta name="keywords" content="Saravena, Arauca, noticias, opinión, tendencias tecnológicas, tendencias económicas, tendencias políticas, educación, recursos web, blog">
    <meta name="author" content="Juan David Sanchez Leal">
    <link rel="canonical" href="">
</head>
<body>
    <div id="main-container">
        <div id="black_top_banner"> <!-- POPULAR -->
            <div class="blog_title">
                <span class="blog_logo">Micro-Blog by Juan David Sanchez Leal</span>
            </div>
            <hr>
            <div class="search_bar">
                <form id="search_form" action="./blog.php" method="get" enctype="multipart/form-data">
                    <input type="hidden" name="search">
                    <input type="text" name="post" id="post">
                    <input type="submit" value="Buscar">
                </form>
            </div>
            <hr>            
            <?php $popular_post = $blogMaker->viewMostPopularPost(); ?>
            <?php if(sizeof($popular_post) == 1){  ?>
                <span class="tag" style="background-color: #F44336;">
                    <a href="blog.php?post=<?php echo $popular_post[0]["id"]; ?>" title="<?php echo $popular_post[0]["title"]; ?>">¡Lo Ultimo!</a>
                </span>
            <?php } ?>
            <span class="tag">
                <a href="blog.php">Explorar</a>
            </span>
            <span class="tag">
                <a target="_blank" href="perfil.html">Autor</a>
            </span>
            <span id="contribuir_on" class="tag">
                <span  onclick="showById('form_new_post');hideById('contribuir_on');showByIdDisplay('contribuir_off', 'inline-block')">Contribuir</span>
            </span>
            <span id="contribuir_off" class="tag">
                <span  onclick="hideById('form_new_post');showByIdDisplay('contribuir_on','inline-block');hideById('contribuir_off')">¡Bueno, Mejor No!</span>
            </span>
            <span id="tag_open_tags" class="tag">
                <a  href="javascript:void(0);"  onclick="showById('tag_container');hideById('tag_open_tags');">Temas</a>
            </span>
            <span class="tag_container" id="tag_container">
                <?php $tags = $blogMaker->viewAllTags(); if(sizeof($tags) != 0){ ?>
                    <?php for($i = 0; $i < sizeof($tags); $i++){ ?>
                        <span class="tag">
                            <a href="blog.php?post=<?php echo $tags[$i]["tag"]; ?>"><?php echo $tags[$i]["tag"]." (".$tags[$i]["cantidad"].")"; ?></a>
                        </span>
                    <?php } ?>
                <?php } ?>
                <span id="tag_close_tags" class="tag" style="background-color: #F44336;">
                    <a  href="javascript:void(0);" style="color: white;"  st onclick="hideById('tag_container');showByIdDisplay('tag_open_tags', 'inline-block');">Cerrar</a>
                </span>
            </span>
            <!--<a id="back_top" href="#" onclick="window.scrollTo({top: 0, behavior: 'smooth' });">Volver/Inicio</a>-->
            <div>
                <form id="form_new_post" action="blog.php" method="post" enctype="multipart/form-data">
                    <label for="title">Titulo:</label>
                    <input required type="text" id="title" name="title">
                    <label for="title">Descripción:</label>
                    <input required maxlength="199" type="text" id="description" name="description">
                    <label for="autor">Autor:</label>
                    <input required type="text" id="autor" name="autor">
                    <label for="readtime">Tiempo de Lectura:</label>
                    <input required type="number" id="readtime" name="readtime">
                    <label for="tag">Tag:</label>
                    <input required type="text" id="tag" name="tag">
                    <label for="password">Seguridad:</label>
                    <input required type="password" id="password" name="password">
                    <label for="postfile">Contenido:</label>
                    <input required type="hidden" id="content" name="content" value="">
                    <input required accept=".basiceditor" type="file" name="postfile" id="postfile">
                    <input type="submit" name="new_post" class="tag" value="Subir Post">
                </form>
            </div>
        </div>
        <!-- Lista el Post si esta en siendo solicitado, y sus posts recomendados. -->
        <?php if(isset($_GET['post']) and is_numeric($_GET['post'])){  $post = $blogMaker->viewPost($_GET['post']); ?>
            <?php if(sizeof($post) == 1){ $posts = $blogMaker->viewRecomendPosts($_GET['post']);  $comments = $blogMaker->viewCommentsByPostId($_GET['post']);  $blogMaker->addViewToPost($_GET['post']); ?>
                <div class="section">
                    <div class="post_view">
                        <div class="post_article">
                            <div class="post_tag"><a href="blog.php?post=<?php echo $post[0]["tag"]; ?>"><?php echo $post[0]["tag"]; ?></a></div>
                            <div class="post_title"><h1><a href="blog.php?post=<?php echo $post[0]["id"]; ?>"><?php echo $post[0]["id"]; ?>: <?php echo $post[0]["title"]; ?></a></h1></div>
                            <div class="post_date"><i><?php echo date("F j, Y, g:i a", strtotime($post[0]["date"])); ?></i></div>
                            <div class="post_autor"><b><?php echo $post[0]["autor"]; ?></b></div>
                            <div class="post_description"><span><i><?php echo $post[0]["description"]; ?></i></span></div>
                            <div class="post_share">
                                <span><a href="https://www.facebook.com/sharer/sharer.php?u=<?php echo $__CURRENTURL; ?>" target="_blank"><img src="./img/icons8-facebook.svg" alt="[Facebook]"></a></span>
                                <span><a href="https://web.whatsapp.com/send?text=<?php echo $__CURRENTURL; ?>" target="_blank"><img src="./img/icons8-whatsapp.svg" alt="[Whatsapp]"></a></span>
                                <span><a href="https://twitter.com/intent/tweet?text=<?php echo $__CURRENTURL; ?>" target="_blank"><img src="./img/icons8-twitterx.svg" alt="[X (twitter)]"></a></span>
                            </div>
                            <div class="post_content"><?php echo $post[0]["content"]; ?></div>
                            <div class="article_metrics">
                                <span class="post_likes">❤️     <a target="_blank" href="blog.php?post=<?php echo $post[0]["id"]; ?>&like">Bacano</a></span>
                                <span class="post_dislikes">👎  <a target="_blank" href="blog.php?post=<?php echo $post[0]["id"]; ?>&dislike">¡Maluco!</a></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="section"> <!-- COMMENT LIST -->
                    <div class="post_comment_list">
                        <?php $blogMaker->drawCommentsList($comments); ?>
                    </div>
                    <form id="post_share_comment" class="post_share_comment" action="./blog.php?post=<?php echo $post[0]["id"]; ?>" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="post" value="<?php echo $post[0]["id"]; ?>">
                        <label for="autor">Autor</label>
                        <label for="comment">Comentario</label>
                        <label for="emoji">Icono</label>
                        <input required type="text" maxlength="255" name="autor" value="">
                        <input required type="text" maxlength="500" name="comment" value="">
                        <select name="emoji" id="emoji">
                            <option value="🧑">🧑 - Hombre</option>
                            <option value="👩">👩 - Mujer</option>
                            <option value="🕹️">🕹️ - Juegos</option>
                            <option value="🐉">🐉 - Fantasía</option>
                            <option value="🧁">🧁 - Magdalena</option>
                            <option value="😤">😤 - Indignante</option>
                            <option value="😡">😡 - Enojos</option>
                            <option value="😀">😀 - Felicidad</option>
                            <option value="❤️">❤️ - Me gusta</option>
                            <option value="🤢">🤢 - Repugnante</option>
                            <option value="🤔">🤔 - Ummmm!!??</option>
                            <option value="🤡">🤡 - Burlas</option>
                        </select>
                        <span style="grid-column-start: 1;grid-column-end: 3;"><b>*</b><small><i>Bienvenido por favor, sé respetuoso y cortés en tus interacciones. Evita ser ofensivo, atacar o acosar a otros.</i></small></span>
                        <input type="submit" name="new_comment" value="Subir">
                    </form>
                </div>
                <div class="section"> <!-- POST LIST -->
                    <div class="post_list">
                        <style> 
                            .post:first-child {
                                grid-column: span 1;
                            }
                            .post:first-child .post_title {
                                font-size: large;
                            }
                        </style>
                         <!-- Style to prevent the Child Presentation. -->
                        <?php $blogMaker->drawPostsList($posts); ?>
                    </div>
                </div>
            <?php }else{ ?>
                <div class="section">
                    <h1 style="grid-column: span 2;">404</h1>
                    <h3 style="grid-column: span 2;">Lo sentimos. No se han encontrado el Post.</h3>
                </div>
            <?php } ?>
        <!-- Lista los posts por tag. -->
        <?php }else if(isset($_GET['post']) and !is_numeric($_GET['post'])){ ?>
            <?php if(isset($_GET['search']) and empty($_GET['search'])){ $posts = $blogMaker->viewSearchedPosts($_GET['post']); }else{ $posts = $blogMaker->viewPostsByTag($_GET['post']); }?>
            <div class="section"> <!-- POST LIST -->
                <div class="post_list">
                    <?php $blogMaker->drawPostsList($posts); ?>
                </div>
            </div>
        <!-- Lista los posts por tag. -->
        <?php }else{  $posts = $blogMaker->viewAllPosts(); ?>
            <div class="section"> <!-- POST LIST -->
                <div class="post_list">
                    <?php $blogMaker->drawPostsList($posts); ?>
                </div>
            </div>
        <?php }  ?>
        <footer>
            <i>

                <div>2023 &#174; Juan D. Sanchez L. </div>
                <div>e-mail: jdsljuan@gmail.com</div>
                <div>Saravena - Arauca</div>
            </i>
        </footer>
    </div>
</body>
<script>
    const fileInput = document.getElementById('postfile');
    fileInput.addEventListener('change', (event) => {
        const file = event.target.files[0];
        const reader = new FileReader();

        reader.onload = (event) => {
            const contents = event.target.result;
            document.getElementById('content').value = contents;
        };

        reader.onerror = (event) => {
            console.error('Error occurred while reading the file', event.target.error);
        };

        reader.readAsText(file);
    });

    function hideById(id){
        document.getElementById(id).style.display = "none";
        document.getElementById(id).style.visibility = "hidden";
    }

    function showById(id){
        document.getElementById(id).style.display = 'inherit';
        document.getElementById(id).style.visibility = 'visible';
    }

    function showByIdDisplay(id, display){
        document.getElementById(id).style.display = display;
        document.getElementById(id).style.visibility = 'visible';
    }

    //const now = new Date();
    //const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    //const formattedDate = now.toLocaleDateString('es-ES', options);
    //document.getElementById('localpc_time').innerText = formattedDate;
</script>
</html>
<?php  //$conn->close(); ?>