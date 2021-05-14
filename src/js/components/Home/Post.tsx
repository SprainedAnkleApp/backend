import { Icon, KebabMenu, Card } from '../common';
import { BsFillChatFill, BsFillHeartFill, BsEyeFill } from 'react-icons/bs';
import cx from 'classnames';

import styles from './Post.module.css';
import React, { useContext } from 'react';
import { Post as PostType } from '../../models/interfaces';
import { userContext } from '../../contexts/CurrentUser';

export type PostProps = PostType & {
  className?: string;
};

const Post = ({
  photoPath,
  content,
  timestamp,
  liked,
  comments,
  watch,
  className,
}: PostProps) => {
  const { user } = useContext(userContext);
  const getContent = () => {
    if (photoPath)
      return (
        <img src={photoPath} alt="post content" className={styles.photo} />
      );
    return <span>{content}</span>;
  };
  return (
    <Card className={className}>
      <div className={styles.header}>
        <Icon url={user?.profilePhoto} variant="s" />
        <div className={styles.nameWithTime}>
          <span className={styles.userName}>{user?.login}</span>
          <span className={styles.time}>{timestamp}</span>
        </div>
        <KebabMenu className={styles.kebab} />
      </div>
      <div className={styles.content}>{getContent()}</div>
      <div className={styles.buttons}>
        <div className={styles['button-container']}>
          <button
            className={cx(styles['button-icon'], styles.heart)}
            onClick={() => console.log('like')}
          >
            <BsFillHeartFill />
          </button>
          <span className={styles['button-text']}>{liked}</span>
        </div>
        <div className={styles['button-container']}>
          <button
            className={styles['button-icon']}
            onClick={() => console.log('comments')}
          >
            <BsFillChatFill />
          </button>
          <span className={styles['button-text']}>{comments}</span>
        </div>
        <div className={cx(styles['button-container'], styles.watch)}>
          <button
            className={styles['button-icon']}
            onClick={() => console.log('watch')}
          >
            <BsEyeFill />
          </button>
          <span className={styles['button-text']}>{watch}</span>
        </div>
      </div>
    </Card>
  );
};

export default Post;