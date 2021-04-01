import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CzCzl3DetailComponent } from './cz-czl-3-detail.component';

describe('Component Tests', () => {
  describe('CzCzl3 Management Detail Component', () => {
    let comp: CzCzl3DetailComponent;
    let fixture: ComponentFixture<CzCzl3DetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CzCzl3DetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ czCzl3: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CzCzl3DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CzCzl3DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load czCzl3 on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.czCzl3).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
