import * as dayjs from 'dayjs';

export interface ICheckinAccount {
  id?: number;
  account?: string;
  roomn?: string | null;
  indatetime?: dayjs.Dayjs | null;
  gotime?: dayjs.Dayjs | null;
  kfang?: number | null;
  dhua?: number | null;
  minin?: number | null;
  peich?: number | null;
  qit?: number | null;
  total?: number | null;
}

export class CheckinAccount implements ICheckinAccount {
  constructor(
    public id?: number,
    public account?: string,
    public roomn?: string | null,
    public indatetime?: dayjs.Dayjs | null,
    public gotime?: dayjs.Dayjs | null,
    public kfang?: number | null,
    public dhua?: number | null,
    public minin?: number | null,
    public peich?: number | null,
    public qit?: number | null,
    public total?: number | null
  ) {}
}

export function getCheckinAccountIdentifier(checkinAccount: ICheckinAccount): number | undefined {
  return checkinAccount.id;
}
