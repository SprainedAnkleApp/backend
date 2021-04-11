import { Header } from '../../components/common/Header';
import { Friends, NewPost } from '../../components/Home';
import Image from '../../../images/mountain.jpg';

import styles from './Home.module.css';

const Home = () => {
  const text = [...Array(70).keys()].map((number) => <p>number</p>);
  const user = {
    userName: 'Bartosz Kaszuba',
    photoUrl: Image,
  };
  return (
    <div classname={styles.main}>
      <Header selected={'home'} user={user} />
      <div className={styles.home}>
        <Friends />
        <NewPost user={user} />
      </div>
      <div>{text}</div>
    </div>
  );
};

export default Home;
