import dayjs from 'dayjs';

export interface IAppointment {
  id?: number;
  startTime?: dayjs.Dayjs | null;
  period?: string | null;
}

export const defaultValue: Readonly<IAppointment> = {};
