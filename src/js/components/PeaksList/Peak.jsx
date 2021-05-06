import { Link } from 'react-router-dom';

import styles from './Peak.module.css';

const Peak = ({ peak }) => {
  return (
    <Link className={styles.card} to={`/peaks/${peak.id}`}>
      <img className={styles.photo} src={peak.photo} alt="mountain" />
      <div className={styles.peakName}>{peak.name}</div>
      <div className={styles.peakHeight}>{peak.height} m n.p.m.</div>
    </Link>
  );
};

export default Peak;
