import { Header } from '../../components/common/Header';
import {
  Friends,
  Posts,
  Achievements,
  ChatWindow,
} from '../../components/Home';
import Image from '../../../images/mountain.jpg';

import styles from './Home.module.css';
import { useState, useEffect } from 'react';
import { getCurrentUser } from '../../API/user/methods';
import { Switch, Route, useLocation } from 'react-router';
import { PeaksList } from '../PeaksList';
import React from 'react';
import { User } from '../../models/interfaces';
import { PeakDetails } from '../Peak';

import cx from 'classnames';

const Home = () => {
  const [user, setUser] = useState<User | null>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [activeChatId, setActiveChatId] = useState<number | null>(null);
  const location = useLocation<Location>();

  useEffect(() => {
    const fetchUser = async () => {
      const userData = await getCurrentUser();
      if (!userData) {
        setUser(null);
        return;
      }
      userData.profilePhoto = userData.profilePhoto || Image;
      setUser(userData);
    };
    fetchUser();
  }, []);

  if (!user) return <div>Loading</div>;
  return (
    <div className={styles.main}>
      <Header
        searchTerm={searchTerm}
        onChangeSearchTerm={(value) => setSearchTerm(value)}
      />
      <div className={styles.home}>
        <Friends
          searchTerm={searchTerm}
          startChat={(friendId: number) => setActiveChatId(friendId)}
          activeChatId={activeChatId}
        />
        <Switch>
          <Route path="/peaks/:id">
            <PeakDetails />
          </Route>
          <Route path="/peaks">
            <PeaksList />
          </Route>
          <Route path="/chat">
            <ChatWindow className={styles.central} />
          </Route>
          <Route path="/">
            <Posts className={styles.central} />
          </Route>
        </Switch>
        <Achievements />
        {location.pathname !== '/chat' && activeChatId !== null && (
          <ChatWindow
            className={styles.chat}
            onClose={() => setActiveChatId(null)}
          />
        )}
      </div>
    </div>
  );
};

export default Home;
