/*
 * Copyright (c) 2010-2014 Marand d.o.o. (www.marand.com)
 *
 * This file is part of Think!Med Clinical Medication Management.
 *
 * Think!Med Clinical Medication Management is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Think!Med Clinical Medication Management is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Think!Med Clinical Medication Management.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.marand.thinkmed.medications;

import java.util.concurrent.ConcurrentMap;

import com.marand.maf.core.data.object.HourMinuteDto;
import com.marand.maf.core.prefs.MafPrefsStorageType;
import com.marand.maf.core.prefs.Preference;
import com.marand.maf.core.prefs.Preferences;
import com.marand.thinkmed.api.core.data.Named;
import com.marand.thinkmed.medications.dto.AdministrationTimingDto;
import com.marand.thinkmed.medications.dto.RoundsIntervalDto;

import static com.marand.thinkmed.medications.dto.AdministrationTimingDto.AdministrationTimestampsDto;

/**
 * @author Mitja Lapajne
 */
public class MedicationPreferencesUtil
{
  private static final String ROUNDS_START_PREFIX = "ROUNDS_START_";
  private static final String ADMINISTRATION_INTERVALS = "ADMINISTRATION_INTERVALS_";
  private static final String SESSION_PREFIX = "SESSION_";
  private static final String OE_PREFIX = "OE_";
  private static final RoundsIntervalDto ROUNDS_INTERVAL_DEFAULT = getDefaultRoundsIntervalDto();
  private static final AdministrationTimingDto ADMINISTRATION_TIMING_DEFAULT = getDefaultAdministrationTimingDto();

  private MedicationPreferencesUtil()
  {
  }

  public static RoundsIntervalDto getRoundsInterval(
      final Long sessionId,
      final Named organizationalEntity)
  {
    final String sessionKey = ROUNDS_START_PREFIX + SESSION_PREFIX + sessionId;
    final RoundsIntervalDto sessionRounds = getRoundsIntervalPreference(sessionKey, null).getValue();
    if (sessionRounds != null)
    {
      return sessionRounds;
    }
    final String oeKey = ROUNDS_START_PREFIX + OE_PREFIX + organizationalEntity.name();
    return getRoundsIntervalPreference(oeKey, ROUNDS_INTERVAL_DEFAULT).getValue();
  }

  public static AdministrationTimingDto getAdministrationTiming(
      final Long sessionId,
      final Named organizationalEntity)
  {
    final String sessionKey = ADMINISTRATION_INTERVALS + SESSION_PREFIX + sessionId;
    final AdministrationTimingDto sessionTiming = getTimingPreference(sessionKey, null).getValue();
    if (sessionTiming != null)
    {
      return sessionTiming;
    }
    final String oeKey = ADMINISTRATION_INTERVALS + OE_PREFIX + organizationalEntity.name();
    return getTimingPreference(oeKey, ADMINISTRATION_TIMING_DEFAULT).getValue();
  }

  private static RoundsIntervalPreference getRoundsIntervalPreference(
      final String key,
      final RoundsIntervalDto defaultValue)
  {
    final RoundsIntervalPreference newPreference = new RoundsIntervalPreference(
        key,
        MafPrefsStorageType.SYSTEM,
        false,
        defaultValue);
    return Preferences.dynamicPreference(key, newPreference);
  }

  private static MedicationAdministrationTimingPreference getTimingPreference(
      final String key,
      final AdministrationTimingDto defaultValue)
  {
    final MedicationAdministrationTimingPreference newPreference = new MedicationAdministrationTimingPreference(
        key,
        MafPrefsStorageType.SYSTEM,
        false,
        defaultValue);
    return Preferences.dynamicPreference(key, newPreference);
  }

  private static <P extends Preference> P getPreference(final ConcurrentMap<String, P> preferences, final String key, final P newPreference)
  {
    final P existingPreference = preferences.putIfAbsent(key, newPreference);
    return existingPreference != null ? existingPreference : newPreference;
  }

  public static RoundsIntervalDto getDefaultRoundsIntervalDto()
  {
    final RoundsIntervalDto roundsDto = new RoundsIntervalDto();
    roundsDto.setStartHour(8);
    roundsDto.setStartMinute(0);
    roundsDto.setEndHour(17);
    roundsDto.setEndMinute(0);
    return roundsDto;
  }

  public static AdministrationTimingDto getDefaultAdministrationTimingDto()
  {
    final AdministrationTimingDto timingDto = new AdministrationTimingDto();
    timingDto.getTimestampsList().add(getMorningTiming());
    timingDto.getTimestampsList().add(getNoonTiming());
    timingDto.getTimestampsList().add(get2XTiming());
    timingDto.getTimestampsList().add(get3XTiming());
    timingDto.getTimestampsList().add(get4XTiming());
    timingDto.getTimestampsList().add(get4HTiming());
    timingDto.getTimestampsList().add(get6HTiming());
    timingDto.getTimestampsList().add(get8HTiming());
    timingDto.getTimestampsList().add(get12HTiming());
    timingDto.getTimestampsList().add(getEveningTiming());
    return timingDto;
  }

  private static AdministrationTimestampsDto getMorningTiming()
  {
    final AdministrationTimestampsDto timestampsDto = new AdministrationTimestampsDto();
    timestampsDto.setFrequency("MORNING");
    timestampsDto.getTimesList().add(new HourMinuteDto(8, 0));
    return timestampsDto;
  }

  private static AdministrationTimestampsDto getNoonTiming()
  {
    final AdministrationTimestampsDto timestampsDto = new AdministrationTimestampsDto();
    timestampsDto.setFrequency("NOON");
    timestampsDto.getTimesList().add(new HourMinuteDto(12, 0));
    return timestampsDto;
  }

  private static AdministrationTimestampsDto get2XTiming()
  {
    final AdministrationTimestampsDto timestampsDto = new AdministrationTimestampsDto();
    timestampsDto.setFrequency("2X");
    timestampsDto.getTimesList().add(new HourMinuteDto(8, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(20, 0));
    return timestampsDto;
  }

  private static AdministrationTimestampsDto get3XTiming()
  {
    final AdministrationTimestampsDto timestampsDto = new AdministrationTimestampsDto();
    timestampsDto.setFrequency("3X");
    timestampsDto.getTimesList().add(new HourMinuteDto(8, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(13, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(20, 0));
    return timestampsDto;
  }

  private static AdministrationTimestampsDto get4XTiming()
  {
    final AdministrationTimestampsDto timestampsDto = new AdministrationTimestampsDto();
    timestampsDto.setFrequency("4X");
    timestampsDto.getTimesList().add(new HourMinuteDto(8, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(13, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(17, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(21, 0));
    return timestampsDto;
  }

  private static AdministrationTimestampsDto get4HTiming()
  {
    final AdministrationTimestampsDto timestampsDto = new AdministrationTimestampsDto();
    timestampsDto.setFrequency("4H");
    timestampsDto.getTimesList().add(new HourMinuteDto(0, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(4, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(8, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(12, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(16, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(20, 0));
    return timestampsDto;
  }

  private static AdministrationTimestampsDto get6HTiming()
  {
    final AdministrationTimestampsDto timestampsDto = new AdministrationTimestampsDto();
    timestampsDto.setFrequency("6H");
    timestampsDto.getTimesList().add(new HourMinuteDto(0, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(6, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(12, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(18, 0));
    return timestampsDto;
  }

  private static AdministrationTimestampsDto get8HTiming()
  {
    final AdministrationTimestampsDto timestampsDto = new AdministrationTimestampsDto();
    timestampsDto.setFrequency("8H");
    timestampsDto.getTimesList().add(new HourMinuteDto(0, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(8, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(16, 0));
    return timestampsDto;
  }

  private static AdministrationTimestampsDto get12HTiming()
  {
    final AdministrationTimestampsDto timestampsDto = new AdministrationTimestampsDto();
    timestampsDto.setFrequency("12H");
    timestampsDto.getTimesList().add(new HourMinuteDto(9, 0));
    timestampsDto.getTimesList().add(new HourMinuteDto(21, 0));
    return timestampsDto;
  }

  private static AdministrationTimestampsDto getEveningTiming()
  {
    final AdministrationTimestampsDto timestampsDto = new AdministrationTimestampsDto();
    timestampsDto.setFrequency("EVENING");
    timestampsDto.getTimesList().add(new HourMinuteDto(21, 0));
    return timestampsDto;
  }
}