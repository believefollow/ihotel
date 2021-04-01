import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CzCzl2DetailComponent } from './cz-czl-2-detail.component';

describe('Component Tests', () => {
  describe('CzCzl2 Management Detail Component', () => {
    let comp: CzCzl2DetailComponent;
    let fixture: ComponentFixture<CzCzl2DetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CzCzl2DetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ czCzl2: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CzCzl2DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CzCzl2DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load czCzl2 on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.czCzl2).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
