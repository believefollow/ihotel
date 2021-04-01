import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccDetailComponent } from './acc-detail.component';

describe('Component Tests', () => {
  describe('Acc Management Detail Component', () => {
    let comp: AccDetailComponent;
    let fixture: ComponentFixture<AccDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AccDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ acc: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AccDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load acc on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.acc).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
