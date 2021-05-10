import styles from './Achievement.module.css';
import { ProgressBar } from '../common';
import { FaMedal } from 'react-icons/fa';

import cx from 'classnames';

const Achievement = ({ className, name, progress }) => {
  return (
    <div className={cx(styles.wrapper, className)}>
      <FaMedal className={styles.icon} size={35} color="#ffd700" />
      <span className={styles.name}>{name}</span>
      <ProgressBar
        className={styles.progressBar}
        backgroundColor="#7A7A7A"
        percentage={Math.round(progress * 100)}
        completed={progress === 1}
      />
    </div>
  );
};

export default Achievement;
