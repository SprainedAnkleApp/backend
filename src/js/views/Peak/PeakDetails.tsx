import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import PeakMap from '../../components/Peak/PeakMap';
import styles from './PeakDetails.module.css';
import {
  getFirstConqueror,
  getPeak,
  getPeakTotalCompletions,
} from '../../API/peaks/methods';
import PeakDescription from '../../components/Peak/PeakDescription';
import { Peak as PeakType, User } from '../../models/interfaces';
import { Peak } from '../../components/PeaksList';
import PeakNavBar from '../../components/Peak/PeakNavBar';

import cx from 'classnames';

export type peakInformations = 'description' | 'map' | 'posts';

export type PeakDetailsProps = {
  className: string;
};

const PeakDetails = ({ className }: PeakDetailsProps) => {
  const { id } = useParams<{ id: string }>();
  const [peakDetails, setPeakDetails] = useState<PeakType | undefined>(
    undefined
  );
  const [firstConqueror, setFirstConqueror] = useState<User | undefined>(
    undefined
  );
  const [totalCompletions, setTotalCompletions] = useState<number | undefined>(
    undefined
  );
  const [state, setState] = useState<peakInformations>('description');

  useEffect(() => {
    const fetchPeak = async () => {
      setPeakDetails(await getPeak(id));
    };
    fetchPeak();
    const getPeakStatistics = async () => {
      setFirstConqueror(await getFirstConqueror(id));
      setTotalCompletions(await getPeakTotalCompletions(id));
    };
    getPeakStatistics();
  }, []);

  if (!peakDetails) return null;

  return (
    <div className={cx(styles.container, className)}>
      <Peak peak={peakDetails} redirectTo={'/peaks'} className={styles.card} />
      <PeakNavBar state={state} setState={setState} />
      <div className={styles.peakInformation}>
        {state === 'description' && (
          <PeakDescription
            peak={peakDetails}
            key={peakDetails.name}
            firstConqueror={firstConqueror}
            totalCompletions={totalCompletions}
          />
        )}
        {state === 'map' && (
          <PeakMap center={[peakDetails.latitude, peakDetails.longitude]} />
        )}
        {state === 'posts' && <p>Posts</p>}
      </div>
    </div>
  );
};

export default PeakDetails;
