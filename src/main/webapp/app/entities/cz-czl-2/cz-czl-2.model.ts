import * as dayjs from 'dayjs';

export interface ICzCzl2 {
  id?: number;
  dr?: dayjs.Dayjs | null;
  type?: string | null;
  fs?: number | null;
  kfl?: number | null;
  fzsr?: number | null;
  pjz?: number | null;
  fsM?: number | null;
  kflM?: number | null;
  fzsrM?: number | null;
  pjzM?: number | null;
  fsY?: number | null;
  kflY?: number | null;
  fzsrY?: number | null;
  pjzY?: number | null;
  fsQ?: number | null;
  kflQ?: number | null;
  fzsrQ?: number | null;
  pjzQ?: number | null;
  dateY?: string | null;
  dqdate?: dayjs.Dayjs | null;
  empn?: string | null;
  number?: number;
  numberM?: number;
  numberY?: number;
  hoteldm?: string | null;
  isnew?: number | null;
}

export class CzCzl2 implements ICzCzl2 {
  constructor(
    public id?: number,
    public dr?: dayjs.Dayjs | null,
    public type?: string | null,
    public fs?: number | null,
    public kfl?: number | null,
    public fzsr?: number | null,
    public pjz?: number | null,
    public fsM?: number | null,
    public kflM?: number | null,
    public fzsrM?: number | null,
    public pjzM?: number | null,
    public fsY?: number | null,
    public kflY?: number | null,
    public fzsrY?: number | null,
    public pjzY?: number | null,
    public fsQ?: number | null,
    public kflQ?: number | null,
    public fzsrQ?: number | null,
    public pjzQ?: number | null,
    public dateY?: string | null,
    public dqdate?: dayjs.Dayjs | null,
    public empn?: string | null,
    public number?: number,
    public numberM?: number,
    public numberY?: number,
    public hoteldm?: string | null,
    public isnew?: number | null
  ) {}
}

export function getCzCzl2Identifier(czCzl2: ICzCzl2): number | undefined {
  return czCzl2.id;
}
