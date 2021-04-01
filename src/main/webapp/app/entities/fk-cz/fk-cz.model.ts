import * as dayjs from 'dayjs';

export interface IFkCz {
  id?: number;
  hoteltime?: dayjs.Dayjs;
  wxf?: number | null;
  ksf?: number | null;
  kf?: number | null;
  zfs?: number | null;
  groupyd?: number | null;
  skyd?: number | null;
  ydwd?: number | null;
  qxyd?: number | null;
  isnew?: number | null;
  hoteldm?: string | null;
  hys?: number | null;
  khys?: number | null;
}

export class FkCz implements IFkCz {
  constructor(
    public id?: number,
    public hoteltime?: dayjs.Dayjs,
    public wxf?: number | null,
    public ksf?: number | null,
    public kf?: number | null,
    public zfs?: number | null,
    public groupyd?: number | null,
    public skyd?: number | null,
    public ydwd?: number | null,
    public qxyd?: number | null,
    public isnew?: number | null,
    public hoteldm?: string | null,
    public hys?: number | null,
    public khys?: number | null
  ) {}
}

export function getFkCzIdentifier(fkCz: IFkCz): number | undefined {
  return fkCz.id;
}
