import styles from './Posts.module.css';
import { Post } from '.';
import { useState, useEffect } from 'react';
import { getPosts } from '../../API/wall/methods';

const Posts = ({ user }) => {
  const [posts, setPosts] = useState([]);

  useEffect(async () => {
    const posts = await getPosts();
    setPosts(posts);
  }, []);

  const renderPosts = () => posts.map((post) => <Post {...post} className={styles.post} />);

  return <div className={styles.wrapper}>{renderPosts()}</div>;
};

export default Posts;
