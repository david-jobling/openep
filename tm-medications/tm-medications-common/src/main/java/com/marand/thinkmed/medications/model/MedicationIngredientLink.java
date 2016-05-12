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

package com.marand.thinkmed.medications.model;

import com.marand.maf.core.data.entity.EffectiveEntity;

/**
 * @author Mitja Lapajne
 */
public interface MedicationIngredientLink extends EffectiveEntity
{
  MedicationIngredient getIngredient();

  void setIngredient(MedicationIngredient ingredient);

  Medication getMedication();

  void setMedication(Medication medication);

  Double getStrengthNumerator();

  void setStrengthNumerator(Double strengthNumerator);

  String getStrengthNumeratorUnit();

  void setStrengthNumeratorUnit(String strengthNumeratorUnit);

  Double getStrengthDenominator();

  void setStrengthDenominator(Double strengthDenominator);

  String getStrengthDenominatorUnit();

  void setStrengthDenominatorUnit(String strengthDenominatorUnit);

  boolean isDescriptive();

  void setDescriptive(boolean descriptive);
}
