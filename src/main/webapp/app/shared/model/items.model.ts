export interface IItems {
  id?: number;
  name?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<IItems> = {};
