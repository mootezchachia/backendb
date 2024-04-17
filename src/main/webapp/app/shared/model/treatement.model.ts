export interface ITreatement {
  id?: number;
  name?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<ITreatement> = {};
